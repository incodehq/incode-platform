#!/usr/bin/env bash
mvn datanucleus:enhance@process-classes datanucleus:enhance@process-test-classes -Denhance -Dskip.default  -Drevision=1.0.0-SNAPSHOT -o $*

