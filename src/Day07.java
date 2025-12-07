void main() throws IOException {
    var lines = Files.readAllLines(Path.of("input07.txt"));

    int startX = lines.getFirst().indexOf('S');
    var manifolds = new HashMap<Integer, List<Integer>>();
    for (int y = 0; y < lines.size(); y++) {
        var current = lines.get(y);
        int x = -1;
        while ((x = current.indexOf('^', x + 1)) != -1) {
            manifolds.computeIfAbsent(y, _ -> new ArrayList<>())
                .add(x);
        }
    }

    solve(startX, lines, manifolds);
}

private static void solve(int startX, List<String> lines, HashMap<Integer, List<Integer>> manifolds) {
    var currentBeams = new HashSet<Integer>();
    currentBeams.add(startX);
    var currentUniverses = new HashMap<Integer, Long>();
    currentUniverses.put(startX, 1L);
    var currentY = 0;

    var splits = 0;
    while (currentY != lines.size()) {
        currentY++;
        if (!manifolds.containsKey(currentY)) continue; // No splits here

        var nextBeams = new HashSet<Integer>();
        var nextUniverses = new HashMap<Integer, Long>();
        var line = manifolds.get(currentY);
        for (var beam : currentBeams) {
            if (line.contains(beam)) {
                splits++;
                nextBeams.add(beam - 1);
                nextBeams.add(beam + 1);
                nextUniverses.merge(beam - 1, currentUniverses.get(beam), Long::sum);
                nextUniverses.merge(beam + 1, currentUniverses.get(beam), Long::sum);
            } else {
                nextBeams.add(beam);
                nextUniverses.merge(beam, currentUniverses.get(beam), Long::sum);
            }
        }
        currentBeams = nextBeams;
        currentUniverses = nextUniverses;
    }
    System.out.println(splits);
    System.out.println(currentUniverses.values().stream().mapToLong(i -> i).sum());
}