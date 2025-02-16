import org.nvk.hashing.HashCalculator;

public class TestHashFunction {
    public static void main(String[] args) {
        String [] data = {"villa", "bangalloo", "apartment", "tent", "house"};

        for (String roomname :data) {
            HashCalculator.HashResult result = HashCalculator.selectWorkerByHashing("bob", roomname);

            System.out.println(result.worker_offset);
        }
    }
}
