## Running

To run this application from sbt, simply run `sbt run`. Alternatively, you can
create a JAR file at target/scala-2.12/resttest.jar by running `sbt assembly`.
You can then run the application with `java -jar target/scala-2.12/resttest.jar`.
Any warnings/errors will be printed to STDERR, allowing you to separate them from
regular program output using output redirection (e.g., `java ... 2>/dev/null`
will hide all warnings and errors if running from a Unix system).

To just run the unit tests, run `sbt test`.

## Command Line Arguments

This application takes a single optional command line argument, allowing you to
specify an alternative REST endpoint to use. If none is provided, the default
"http://resttest.bench.co/transactions/" will be used.

