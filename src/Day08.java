void main() throws IOException {
    var input = Files.lines(Path.of("input08.txt"));

    var points = input.map(Point::new).toList();

    var distances = calculateDistances(points);
    var connections = connectLimit(distances);
    part1(connections);
    part2(points.size(), distances, connections);
}

private static void part1(Map<Point, Set<Point>> connections) {
    var result = connections.values()
        .stream()
        .distinct()
        .map(Set::size)
        .sorted(Collections.reverseOrder())
        .mapToInt(i -> i)
        .limit(3)
        .reduce(1, (a, b) -> a * b);
    System.out.println(result);
}

private void part2(int size, List<Distance> distances, Map<Point, Set<Point>> connections) {
    int i = 1000;
    Distance lastDistance = null;
    while (connections.values().stream().findFirst().orElseThrow().size() != size) {
        lastDistance = connectNext(distances, connections, i);
        i++;
    }
    assert lastDistance != null;
    System.out.println(lastDistance.a().x() * (long) lastDistance.b().x());
}

private Map<Point, Set<Point>> connectLimit(List<Distance> distances) {
    var groups = new HashMap<Point, Set<Point>>();
    for (int i = 0; i < 1000; i++) {
        connectNext(distances, groups, i);
    }
    return groups;
}

private static Distance connectNext(List<Distance> distances, Map<Point, Set<Point>> groups, int i) {
    var distance = distances.get(i);
    var a = distance.a();
    var b = distance.b();
    if (!groups.containsKey(a) && !groups.containsKey(b)) {
        var group = new HashSet<Point>();
        group.add(a);
        group.add(b);
        groups.put(a, group);
        groups.put(b, group);
    } else if (!groups.containsKey(a)) {
        var group = groups.get(b);
        group.add(a);
        groups.put(a, group);
    } else if (!groups.containsKey(b)) {
        var group = groups.get(a);
        group.add(b);
        groups.put(b, group);
    } else {
        var group = groups.get(a);
        var merged = groups.get(b);
        group.addAll(merged);
        groups.put(b, group);
        merged.forEach(p -> groups.put(p, group));
    }
    return distance;
}

private List<Distance> calculateDistances(List<Point> points) {
    var distances = new ArrayList<Distance>();
    for (int i = 0; i < points.size(); i++) {
        for (int j = i + 1; j < points.size(); j++) {
            distances.add(new Distance(points.get(i), points.get(j)));
        }
    }
    distances.sort(Comparator.comparing(Distance::distance));
    return distances;
}

record Point(int x, int y, int z) {
    Point(String line) {
        var parts = line.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int z = Integer.parseInt(parts[2]);
        this(x, y, z);
    }

    double distanceTo(Point point) {
        return Math.hypot(Math.hypot(x - point.x, y - point.y), z - point.z);
    }
}

record Distance(Point a, Point b, double distance) {
    Distance(Point a, Point b) {
        this(a, b, a.distanceTo(b));
    }
}