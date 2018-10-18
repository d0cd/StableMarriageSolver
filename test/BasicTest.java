/* Basic correctness test */

class BasicTest {

    public static void main(String[] args) {
        assert(args.length == 1);
        System.out.println(args[0]);
        GSSolver solver = new GSSolver(args[0]);
        solver.solve();
        solver.printMatchings();
    }
}