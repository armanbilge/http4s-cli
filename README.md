# http4s-cli

Like `python3 -m http.server`, but backed by [http4s](https://http4s.org) ember on Scala.js.

## Install
```
npm i -g http4s-cli
```

## Use

```
Usage: http4s [--bind <ADDRESS>] [--directory <DIRECTORY>] [<port>]

http4s simple static server

Options and flags:
    --help
        Display this help text.
    --version, -v
        Print the version number and exit.
    --bind <ADDRESS>, -b <ADDRESS>
        Specify alternate bind address [default: all interfaces]
    --directory <DIRECTORY>, -d <DIRECTORY>
        Specify alternative directory [default:current directory]
```

## Learn

This repo demonstrates how to publish a Scala.js app to npm using [scala-cli](https://scala-cli.virtuslab.org/).
