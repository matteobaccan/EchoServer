package it.baccan.echoserver;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.microhttp.DebugLogger;
import org.microhttp.EventLoop;
import org.microhttp.Handler;
import org.microhttp.Header;
import org.microhttp.Logger;
import org.microhttp.Options;
import org.microhttp.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.LinkedHashMap;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author Matteo Baccan
 */
@Command(name = "EchoServer", mixinStandardHelpOptions = true, version = "EchoServer 1.1.0", description = "Simply EchoServer for stress test")
public class EchoServer implements Callable<Integer> {

    @Option(names = { "-i", "--ip" }, description = "Ip to use")
    private String ip = "127.0.0.1";

    @Option(names = { "-p", "--port" }, description = "Port to use")
    private int port = 8080;

    @Option(names = { "-c", "--content-type" }, description = "Content-type to use")
    private String contentType = "text/plain";

    @Option(names = { "-b", "--body" }, description = "Body to use")
    private String body = "hello world";

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception { // your business logic goes here...
        final int concurrency = 4;
        final ExecutorService executor = Executors.newFixedThreadPool(concurrency);

        Options options = Options.builder()
                .withHost(ip)
                .withPort(port)
                .withRequestTimeout(Duration.ofSeconds(60))
                .withResolution(Duration.ofMillis(100))
                .withMaxRequestSize(1_024 * 1_024)
                .withAcceptLength(0)
                .withConcurrency(concurrency)
                .build();

        Logger logger = new DebugLogger();

        Handler handler = (req, callback) -> executor.execute(() -> {
            Map <String, Object> bodyMap = new LinkedHashMap<>();
            bodyMap.put("method", req.method());
            bodyMap.put("uri", req.uri());
            bodyMap.put("version", req.version());
            bodyMap.put("headers", req.headers());
            bodyMap.put("body", req.body());
            
            // Convert the body to a JSON string formatted
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String bodyJson = gson.toJson(bodyMap);

            // Create a ByteBuffer from the JSON string
            ByteBuffer bodyBuffer = ByteBuffer.wrap(bodyJson.getBytes());

            Response response = new Response(
                    200,
                    "OK",
                    List.of(new Header("Content-Type", contentType)),
                    bodyBuffer.array());
            
            callback.accept(response);
        });

        EventLoop eventLoop = new EventLoop(options, logger, handler);
        eventLoop.start();
        eventLoop.join();

        return 0;
    }

    /**
     *
     * @param args
     */
    public static void main(String... args) {
        EchoServer echoServer = new EchoServer();
        CommandLine commandLine = new CommandLine(echoServer);
        commandLine.parseArgs(args);

        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(EchoServer.class.getName());
        if (!commandLine.isUsageHelpRequested() && !commandLine.isVersionHelpRequested()) {
            logger.info(String.format(
                    "Start EchoServer%non IP: [%s][%s]%nContent-Type: [%s]%nBody: [%s]%nType EchoServer -h for help%n",
                    echoServer.ip,
                    echoServer.port,
                    echoServer.contentType,
                    echoServer.body));
        }
        int exitCode = commandLine.execute(args);
        System.exit(exitCode);
    }
}
