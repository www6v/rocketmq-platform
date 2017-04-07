#!/usr/bin/env bash
imageName=$1
containerName=$2
networkName=$3

# clear container
docker stop $containerName
docker rm $containerName


docker run -d -v /home/admin/hdp-docker/centos/merge/pipeline/workspace:/usr/local/workspace --network $networkName --name $containerName $imageName
