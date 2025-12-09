void main() throws IOException {
    var points = Files.lines(Path.of("input09.txt"))
        .map(Point::new)
        .toList();

    long part1 = Long.MIN_VALUE;
    long part2 = Long.MIN_VALUE;

    for (int i = 0; i < points.size(); i++) {
        for (int j = i + 1; j < points.size(); j++) {
            var a = points.get(i);
            var b = points.get(j);
            var area = a.area(b);
            if (area > part1) {
                part1 = area;
            }
            if (area > part2 && notBroken(points, a, b)) {
                part2 = area;
            }
        }
    }
    System.out.println(part1);
    System.out.println(part2);
}

private boolean notBroken(List<Point> points, Point a, Point b) {
    for (int i = 0; i < points.size(); i++) {
        var point = points.get(i);
        var next = points.get((i + 1) % points.size());

        if (Math.min(a.x(), b.x()) < Math.max(point.x(), next.x()) &&
            Math.max(a.x(), b.x()) > Math.min(point.x(), next.x()) &&
            Math.min(a.y(), b.y()) < Math.max(point.y(), next.y()) &&
            Math.max(a.y(), b.y()) > Math.min(point.y(), next.y())) {
            return false;
        }
    }
    return true;
}

record Point(int x, int y) {
    Point(String line) {
        var parts = line.split(",");
        this(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    long area(Point other) {
        return (Math.abs(other.x - x) + 1L) * (Math.abs(other.y - y) + 1L);
    }
}