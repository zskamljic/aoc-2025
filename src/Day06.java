void main() throws IOException {
    var input = """
        123 328  51 64\s
         45 64  387 23\s
          6 98  215 314
        *   +   *   +\s\s""";
    input = Files.readString(Path.of("input06.txt"));

    var lines = input.split("\n");
    part1(lines);
    part2(lines);
}

private static void part2(String[] lines) {
    var grid = new char[lines.length][];
    for (int i = 0; i < lines.length; i++) {
        grid[i] = lines[i].toCharArray();
    }

    var result = 0L;
    var numbers = new ArrayList<Integer>();
    for (int i = grid[0].length - 1; i >= 0; i--) {
        var number = 0;
        for (var chars : grid) {
            if (chars[i] == ' ') {
                if (number != 0) {
                    numbers.add(number);
                    number = 0;
                }
                continue;
            } else if (!Character.isDigit(chars[i])) {
                if (number != 0) {
                    numbers.add(number);
                }
                var stream = numbers.stream().mapToLong(l -> l);
                if (chars[i] == '+') {
                    result += stream.sum();
                } else if (chars[i] == '*') {
                    result += stream.reduce(1, (a, b) -> a * b);
                }
                numbers.clear();
                number = 0;
            }


            number *= 10;
            number += chars[i] - '0';
        }
    }
    System.out.println(result);
}

private static void part1(String[] lines) {
    var lineValues = new ArrayList<List<String>>();
    for (var line : lines) {
        var items = line.trim().split("\\s+");
        lineValues.add(Arrays.asList(items));
    }

    var operators = lineValues.removeLast();
    var columns = new ArrayList<List<Integer>>();
    for (var line : lineValues) {
        for (int j = 0; j < line.size(); j++) {
            if (columns.size() <= j) columns.add(new ArrayList<>());
            columns.get(j).add(Integer.parseInt(line.get(j).trim()));
        }
    }

    var result = 0L;
    for (int column = 0; column < columns.size(); column++) {
        var line = columns.get(column);
        var stream = line.stream()
            .mapToLong(i -> i);
        if (operators.get(column).equals("+")) {
            result += stream.sum();
        } else {
            result += stream.reduce(1, (a, b) -> a * b);
        }
    }
    System.out.println(result);
}