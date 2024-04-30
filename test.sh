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
  sbt -warn -Dsbt.supershell=false run
}

initFields='str: String'

run "$initFields"
run "$initFields, int: Int"
writeFile "$initFields"
