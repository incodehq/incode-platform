#!/usr/bin/env bash
mvn datanucleus:enhance@process-classes datanucleus:enhance@process-test-classes -Denhance -Dskip.default -o $*

