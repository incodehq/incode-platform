#!/usr/bin/env bash

mvn process-classes -o
python -m webbrowser -t http://localhost:4000 && pushd target/site && python -m http.server 4000 && popd
