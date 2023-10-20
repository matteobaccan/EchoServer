package it.baccan.echoserver;

import java.time.Duration;
import java.util.List;
import org.microhttp.DebugLogger;
import org.microhttp.EventLoop;
import org.microhttp.Handler;
import org.microhttp.Header;
import org.microhttp.Logger;
import org.microhttp.Options;
import org.microhttp.Response;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;

/**
 *
 * @author Matteo Baccan
 */
@Command(name = "EchoServer", mixinStandardHelpOptions = true, version = "EchoServer 1.0.0", description = "Simply EchoServer for stress test")
public class EchoServer implements Callable<Integer> {

    @Option(names = {"-i", "--ip"}, description = "Ip to use")
    private String ip = "127.0.0.1";

    @Option(names = {"-p", "--port"}, description = "Port to use")
    private int port = 8080;

    @Option(names = {"-c", "--content-type"}, description = "Content-type to use")
    private String contentType = "text/plain";

    @Option(names = {"-b", "--body"}, description = "Body to use")
    private String body = "hello world\n";

    @Override
    public Integer call() throws Exception { // your business logic goes here...                                                                                                                                                                                                                                                                                   
        Response response = new Response(
                200,
                "OK",
                List.of(new Header("Content-Type", contentType)),
                body.getBytes());
        
        Options options = Options.builder()
                .withHost(ip)
                .withPort(port)
                .withRequestTimeout(Duration.ofSeconds(60))
                .withResolution(Duration.ofMillis(100))
                //.withBufferSize(1_024 * 64)
                .withMaxRequestSize(1_024 * 1_024)
                .withAcceptLength(0)
                .withConcurrency(4)
                .build();
        
        Logger logger = new DebugLogger();
        Handler handler = (req, callback) -> callback.accept(response);
        EventLoop eventLoop = new EventLoop(options, logger, handler);
        eventLoop.start();
        eventLoop.join();
        return 0;
    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        EchoServer echoServer = new EchoServer();
        CommandLine commandLine = new CommandLine(echoServer);
        commandLine.parseArgs(args);
        if (!commandLine.isUsageHelpRequested() && !commandLine.isVersionHelpRequested()) {
            System.out.printf("Start EchoServer%non IP: [%s][%s]%nContent-Type: [%s]%nBody: [%s]%nType EchoServer -h for help%n",
                    echoServer.ip,
                    echoServer.port,
                    echoServer.contentType,
                    echoServer.body);
        }
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
