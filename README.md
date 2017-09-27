## Running

To run this application from sbt, simply run `sbt run`. Alternatively, you can
create a JAR file at `target/scala-2.12/resttest.jar` by running `sbt assembly`.
You can then run the application with `java -jar target/scala-2.12/resttest.jar`.
Any warnings/errors will be printed to STDERR, allowing you to separate them from
regular program output using output redirection (e.g., `java ... 2>/path/to/file`
will hide all warnings and errors in the console and redirect them to the file
specified).

To just run the unit tests, run `sbt test`.

## Command Line Arguments

This application takes a single optional command line argument, allowing you to
specify an alternative REST endpoint to use. If none is provided, the default
"http://resttest.bench.co/transactions/" will be used.

## Limitations and Tradeoffs

If the application cannot parse a particular transaction, say because
the amount is not a number or the date is not a valid date, a warning will be
printed and the entire page of transactions will be discarded. One improvement
would be to still print the warning, but discard just the individual problematic
transaction.

In order to make downloading all of the pages quicker, I hardcoded the number
of transactions that are expected to appear on a page, and after downloading the
first page to get the total number of transactions, all of the other pages are
downloaded in parallel.

There is an implicit assumption that the number of transactions being returned
by the API will not change between downloading the first and last page. This is
particularly important since the transactions appear, with a few exceptions, in
most recent to least recent order, meaning that if a new transaction were to be
added midway through fetching from the API, it would "bump"
some transactions to a different page. This could, in the worst case, cause some
transactions to be double-counted or missed altogether by our application.
