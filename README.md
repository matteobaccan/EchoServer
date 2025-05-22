# EchoServer

EchoServer is a simple yet powerful tool for stress-testing HTTP requests. It allows you to launch a server that responds to every HTTP request by simply "echoing" (returning) what it receives from the client. This is ideal for debugging, load testing, and analyzing how clients and intermediaries handle HTTP transactions.

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

## Testing All HTTP Methods

You can test the EchoServer with all standard HTTP methods using curl. Replace http://127.0.0.1:8080 with your server address if different.

### GET
```sh
curl -X GET http://127.0.0.1:8080/test
```

### POST
```sh
curl -X POST http://127.0.0.1:8080/test -d '{"message":"Hello"}' -H "Content-Type: application/json"
```

### PUT
```sh
curl -X PUT http://127.0.0.1:8080/test -d '{"message":"Update"}' -H "Content-Type: application/json"
```

### DELETE
```sh
curl -X DELETE http://127.0.0.1:8080/test
```

### PATCH
```sh
curl -X PATCH http://127.0.0.1:8080/test -d '{"message":"Partial update"}' -H "Content-Type: application/json"
```

### OPTIONS
```sh
curl -X OPTIONS -i http://127.0.0.1:8080/test
```

### HEAD
```sh
curl -I http://127.0.0.1:8080/test
```

The server should echo back the request details or respond appropriately for each HTTP method.

## Example Request and Response

**Request:**
```bash
curl -X POST http://127.0.0.1:8080/hello -d "test=123" -H "Custom-Header: foo"
```

**Response:**
```json
{
  "method": "POST",
  "uri": "/hello",
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

---

You can replace your current README.md with this version for improved clarity and HTTP method test instructions. Let me know if you want any further customization!
