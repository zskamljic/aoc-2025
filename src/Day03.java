void main() throws IOException {
    var input = Files.readString(Path.of("input03.txt"));

    var part1 = input.lines()
        .parallel()
        .mapToLong(l -> findMax(l, 2))
        .sum();

    System.out.println(part1);

    var part2 = input.lines()
        .parallel()
        .mapToLong(l -> findMax(l, 12))
        .sum();
    System.out.println(part2);
}

private long findMax(String line, int maxLength) {
    int index = 0;

    var number = 0L;
    for (int digits = 0; digits < maxLength; digits++) {
        int max = -1;
        for (int i = index; i <= line.length() - (maxLength - digits); i++) {
            int val = line.charAt(i);
            if (val > max) {
                max = val;
                index = i + 1;
            }
        }
        number *= 10;
        number += max - '0';
    }

    return number;
}