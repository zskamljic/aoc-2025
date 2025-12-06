void main() throws IOException {
    int dial = 50;

    var movements = Files.lines(Path.of("input01.txt"));

    var values = movements.mapToInt(s -> {
        var value = Integer.parseInt(s.substring(1));
        if (s.startsWith("L")) {
            value = -value;
        }
        return value;
    }).toArray();

    int part1 = 0;
    int part2 = 0;
    for (var value : values) {
        var before = dial;
        dial += value;
        while (dial < 0) {
            dial += 100;
        }
        dial %= 100;
        if (dial == 0) {
            part1++;
            part2++;
        } else if (before != 0 && value < 0 && dial > before || value > 0 && dial < before) {
            part2++;
        }
        part2 += Math.abs(value / 100);
    }
    System.out.println(part1);
    System.out.println(part2);
}