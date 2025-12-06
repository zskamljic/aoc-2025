void main() throws IOException {
    var text = Files.readString(Path.of("input02.txt"));

    var ranges = Arrays.stream(text.trim().split(",")).map(Range::parse)
        .toList();

    var part1 = 0L;
    var part2 = 0L;
    for (var range : ranges) {
        for (long id = range.start(); id <= range.end(); id++) {
            var digits = (int) Math.floor(Math.log10(id)) + 1;

            var added = false;
            digit:
            for (int digit = 1; digit < digits; digit++) {
                if (digits / (float) digit % 1 != 0) continue;

                var divisor = (int) Math.pow(10, digit);
                var check = id % divisor;
                var value = id / divisor;
                while (value > 0) {
                    if (value % divisor != check) continue digit;
                    value /= divisor;
                }
                if (!added) {
                    part2 += id;
                }
                added = true;
                if (digits / (float) digit == 2) {
                    part1 += id;
                }
            }
        }
    }
    System.out.println(part1);
    System.out.println(part2);
}

record Range(long start, long end) {
    static Range parse(String input) {
        var parts = input.split("-");
        return new Range(Long.parseLong(parts[0]), Long.parseLong(parts[1]));
    }
}