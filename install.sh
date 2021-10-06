#!/bin/bash

mkdir -p ~/.local/bin/
chmod +x ./bin/*
ln -s -f $PWD/bin/* ~/.local/bin/
