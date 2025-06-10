#!/bin/bash

if [ "${BASH_SOURCE[0]}" = "${0}" ]; then
    set -e
fi

export CI_PROJECT_PATH=$(realpath $(dirname $0)/../)
export PROJECT_DIR=$CI_PROJECT_PATH

function update_script {
    rm -rf "${CI_PROJECT_PATH}/docker/functions.sh"
    docker pull eclipse-temurin:17-jre-alpine:latest
    docker run --rm -v $CI_PROJECT_PATH:/config eclipse-temurin:17-jre-alpine:latest bash -c "cp /app/functions.sh /config/docker/"
}

if [ ! -f "${CI_PROJECT_PATH}/docker/functions.sh" ]; then
    update_script
fi

source ${CI_PROJECT_PATH}/docker/functions.sh

export DOCKER_REPO=docker-dev.ekuaibao.com

function help {
    echo "Usage: $0 [command]"
    echo "Commands:"
    echo "  up: Start the services"
    echo "  down: Stop the services"
    echo "  wait: Wait for the dependencies to be ready"
    echo "  logs: Show the logs of the services"
    echo "  compose: Run compose commands"
    echo "  update: Update Docker image and functions.sh"
}

if [ ! "${BASH_SOURCE[0]}" = "${0}" ]; then
    return 0
fi

case $1 in
    up)
        shift
        print_env
        start_services $@
        ;;
    down)
        shift
        stop_services $@
        ;;
    logs)
        shift
        compose logs $@
        ;;
    wait)
        shift
        wait_for_dependencies $@
        ;;
    compose)
        shift
        compose $@
        ;;
    update)
        shift
        update_script
        ;;
    help)
        help
        exit 0
        ;;
    *)
        echo "Invalid command: $1"
        help
        exit 1
        ;;
esac
