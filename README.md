# EchoServer

EchoServer is a simple yet powerful tool for stress-testing HTTP requests. It allows you to launch a server that responds to every HTTP request by simply "echoing" (returning) what it receives from the client. It is useful for testing HTTP clients, automation tools, load balancers, and much more.

## Main Features

- Returns body, headers, and parameters of every HTTP request received
- Configurable for any IP and port
- Supports specifying the body and content-type of the response
- Suitable for load testing and HTTP application debugging

## Requirements

- Java 17 or higher
- Maven (optional, for building from source)

## Installation

Clone the repository:
```bash
git clone https://github.com/matteobaccan/EchoServer.git
cd EchoServer
```

Build the project with Maven:
```bash
mvn clean package
```

## Usage

Start the server with:
```bash
java -jar target/EchoServer.jar --ip=127.0.0.1 --port=8080
```

Or use the available parameters:
```
Usage: EchoServer [-hV] [-b=<body>] [-c=<contentType>] [-i=<ip>] [-p=<port>]
  -b, --body=<body>         Response body
  -c, --content-type=<contentType> Response Content-Type
  -h, --help                Show help and exit
  -i, --ip=<ip>             IP address for the server to listen on
  -p, --port=<port>         Port for the server to listen on
  -V, --version             Show version information and exit
```

Example:
```bash
EchoServer --ip=127.0.0.1 --port=8080
```
This will start the server on IP 127.0.0.1 and port 8080.

## Example Request and Response

**Request:**
```bash
curl -X POST http://127.0.0.1:8080/hello -d "test=123" -H "Custom-Header: foo"
```

**Response:**
```json
{
  "method": "POST",
  "path": "/hello",
  "headers": {
    "Custom-Header": "foo",
    ...
  },
  "body": "test=123",
  ...
}
```

## Contributing

Contributions, bug reports, and pull requests are welcome! Feel free to open issues or propose improvements.

## License

This project is licensed under the MIT License.
