void main() throws IOException {
    var input = Arrays.asList("""
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.""".split("\n"));

    input = Files.readAllLines(Path.of("input04.txt"));

    var rolls = new HashSet<Point>();
    for (int y = 0; y < input.size(); y++) {
        var chars = input.get(y).toCharArray();
        for (int x = 0; x < chars.length; x++) {
            if (chars[x] == '@') {
                rolls.add(new Point(x, y));
            }
        }
    }

    var originalCount = rolls.size();
    var part1 = true;
    while (true) {
        var accessible = new HashSet<Point>();
        for (var roll : rolls) {
            var neighbors = roll.neighbors()
                .stream()
                .filter(rolls::contains)
                .count();
            if (neighbors < 4) {
                accessible.add(roll);
            }
        }
        if (part1) {
            System.out.println(accessible.size());
            part1 = false;
        }
        if (accessible.isEmpty()) {
            break;
        }
        rolls.removeAll(accessible);
    }
    System.out.println(originalCount - rolls.size());
}

record Point(int x, int y) {
    public List<Point> neighbors() {
        var result = new ArrayList<Point>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                result.add(new Point(x + i, y + j));
            }
        }
        return result;
    }
}