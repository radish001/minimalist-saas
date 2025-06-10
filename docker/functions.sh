#!/bin/bash

if [ "${BASH_SOURCE[0]}" = "${0}" ]; then
    echo "This script should be sourced, not executed."
    exit 1
fi

if [ -z "$PROJECT_DIR" ]; then
    echo "PROJECT_DIR is not set."
    return 1
fi

set -E

if command -v docker &> /dev/null; then
  export CONTAINER_CMD="docker"
elif command -v podman &> /dev/null; then
  export CONTAINER_CMD="podman"
else
  echo "Neither docker nor podman is installed."
  exit 1
fi

PROJECT_DIR=$(realpath $PROJECT_DIR)

# Export the docker repository, used by the docker-compose file
export DOCKER_REPO=ekb-repo.tencentcloudcr.com
# Export the bind address of the frps server
export FRP_SERVER_ADDR=""
CONTAINER_ID=""
# Project ID is used to create a unique volume name
PROJECT_ID=$(echo -n "$CI_PROJECT_PATH" | shasum | awk '{print $1}')
DOCKER_VOLUME="${PROJECT_ID}_config"

# Export variables from .env files
if [ -f $PROJECT_DIR/docker/.env ]; then
    export $(cat $PROJECT_DIR/docker/.env | xargs)
fi

if [ -f $PROJECT_DIR/docker/.env.ci ]; then
    export $(cat $PROJECT_DIR/docker/.env.ci | xargs)
fi

function compose {
    $CONTAINER_CMD compose -f $PROJECT_DIR/docker/docker-compose.yaml -p $PROJECT_ID $@
}

function start_services {
    # Create network and volume first
    compose create frp

    # Copy the files to volume
    local DIR
    for DIR in docker db-versions api-specs $ADDITIONAL_CONFIG_DIR; do
        if [ -d $PROJECT_DIR/$DIR ]; then
            echo "Copying $DIR to volume ..."
            compose cp $PROJECT_DIR/$DIR frp:/config/
        fi
    done

    # Connect to network
    if [ ! -z "$CONTAINER_ID" ]; then
        NETWORK_NAME=${PROJECT_ID}_default
        $CONTAINER_CMD network connect $NETWORK_NAME $CONTAINER_ID
        compose rm -f frp
    fi

    # Start frps server
    echo "Starting frps server..."
    pkill frps || true
    frps --bind-addr 0.0.0.0 --bind-port 9834 &
    sleep 5

    # Start docker-compose services
    compose up $@
}

function stop_services {
    if [ ! -z "$CONTAINER_ID" ]; then
        NETWORK_NAME=${PROJECT_ID}_default
        $CONTAINER_CMD network disconnect $NETWORK_NAME $CONTAINER_ID
    fi
    compose down
    pkill frps || true
}

function wait_for_dependencies {
    counter=0

    while true; do
        RES=$(compose logs init-finish | grep 'Dependencies are ready')
        if [ -z "$RES" ]; then
            if [ $counter -ge 60 ]; then
            echo "Dependencies are not up and running. Exiting."
            compose logs
            return 1
            fi
            echo "Dependencies are not up and running. Waiting..."
            sleep 3
            counter=$((counter+1))
        else
            echo "Dependencies are up and running."
            return 0
        fi
    done
}

function find_network_interface {
    local NETWORK_INF=""
    # FRP_SERVER_NETWORK_INF: to work around weird network interface names on Linux
    for NETWORK_INF in $FRP_SERVER_NETWORK_INF eth0 en0; do
        (ifconfig $NETWORK_INF &> /dev/null) && echo $NETWORK_INF && return 0 || true
    done
    echo "Can't find the network interface" > /dev/stderr
    return 1
}

function find_container_by_ip {
    for ID in $($CONTAINER_CMD ps --format '{{.ID}}'); do
        IPs=$($CONTAINER_CMD inspect $ID -f json | jq -r '.[].NetworkSettings.Networks.[].IPAddress')
        for IP in $IPs; do
            if [ "$IP" = "$1" ]; then
                echo $ID
                return 0
            fi
        done
    done
}

NETWORK_INTERFACE=$(find_network_interface)
FRP_SERVER_ADDR=$(ifconfig $NETWORK_INTERFACE | grep 'inet ' | awk '{print $2}')
CONTAINER_ID=$(find_container_by_ip $FRP_SERVER_ADDR)

function print_env {
    echo "Project directory: $PROJECT_DIR"
    echo "Project Cache ID: $PROJECT_ID"
    echo "Docker volume: $DOCKER_VOLUME"
    echo "Docker repository: $DOCKER_REPO"
    echo "FRP Server IP: $FRP_SERVER_ADDR"
    echo "Container ID: $CONTAINER_ID"
}
