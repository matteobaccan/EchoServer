# EchoServer

The HTTP Echo Server is a powerful tool for stress testing. It allows users to create an echo server that will relay all HTTP requests.

## What is an HTTP Echo server

An HTTP Echo Server is a type of server that responds to a client's request by echoing back what was sent. The purpose of an HTTP Echo Server is to test the functionality of a client's HTTP requests. This type of server is most often used in development and testing environments, since it offers a quick way to test the communication between two systems.

## Usage

```text
Usage: EchoServer [-hV] [-b=<body>] [-c=<contentType>] [-i=<ip>] [-p=<port>]
Simply EchoServer for stress test
  -b, --body=<body>   Body to use
  -c, --content-type=<contentType>
                      Content-type to use
  -h, --help          Show this help message and exit.
  -i, --ip=<ip>       Ip to use
  -p, --port=<port>   Port to use
  -V, --version       Print version information and exit.
```

## How To use

```bash
EchoServer --ip=127.0.0.1 --port=8080
```

Run an EchoServer on ip 127.0.0.1 and port 8080
