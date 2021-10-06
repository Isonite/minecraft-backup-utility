#!/bin/bash

for i in $(ls bin/)
do
    rm -f ~/.local/bin/$i
done
