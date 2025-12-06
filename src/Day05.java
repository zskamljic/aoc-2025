void main() throws IOException {
    var input = Files.readString(Path.of("input05.txt"));

    var parts = input.split("\n\n");
    var ranges = mergeRanges(parseRanges(parts[0]));
    var ids = parts[1].lines()
        .mapToLong(Long::parseLong)
        .toArray();

    var valid = 0;

    for (var id : ids) {
        for (var range : ranges) {
            if (range.contains(id)) {
                valid++;
                break;
            }
        }
    }
    System.out.println(valid);
    System.out.println(ranges.stream().mapToLong(Range::count).sum());
}

private List<Range> mergeRanges(List<Range> ranges) {
    var remainder = new ArrayList<>(ranges);
    remainder.sort(Comparator.comparing(Range::start));

    var nextRanges = new ArrayList<Range>();
    while (!remainder.isEmpty()) {
        var seed = remainder.removeFirst();
        var start = seed.start();
        var end = seed.end();
        while (!remainder.isEmpty() && remainder.getFirst().start() <= end) {
            var next = remainder.removeFirst();
            if (end < next.end()) {
                end = next.end();
            }
        }
        nextRanges.add(new Range(start, end));
    }
    return nextRanges;
}

private List<Range> parseRanges(String part) {
    var result = new ArrayList<Range>();
    for (var line : part.split("\n")) {
        var items = line.split("-");

        result.add(new Range(Long.parseLong(items[0]), Long.parseLong(items[1])));
    }
    return result;
}

record Range(long start, long end) {
    boolean contains(long value) {
        return value >= start && value <= end;
    }

    public long count() {
        return end - start + 1;
    }
}