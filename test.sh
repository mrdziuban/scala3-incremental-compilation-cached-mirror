#!/usr/bin/env bash

set -eo pipefail

find . -name target -type d | xargs rm -rf

function mkContent() {
  printf "package example

case class Test($1)
"
}

function writeFile() {
  echo "$(mkContent "$1")" > src/main/scala/example/Test.scala
}

function run() {
  writeFile "$1"

  echo -e '\nRunning with fields: `'$1'`'

  sbtCmds=''

  if [ "$2" = 1 ]; then
    sbtCmds="$sbtCmds; clean"
  fi

  sbtCmds="$sbtCmds; run"

  sbt -warn -Dsbt.supershell=false "$sbtCmds"
}

initFields='str: String'

run "$initFields" 1
run "$initFields, int: Int"
writeFile "$initFields"
