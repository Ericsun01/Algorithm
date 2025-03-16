import java.util.BitSet;

public class BloomFilterSample {
    private BitSet bitArray;
    private int[] hashSeeds;
    private int size;

    public BloomFilterSample(int size, int[] seeds) {
        this.size = size;
        this.hashSeeds = seeds;
        bitArray = new BitSet(size);
    }

    public void add(String item) {
        for (int seed: hashSeeds) {
            int hash = hash(item, seed);
            bitArray.set(hash % size);
        }
    }

    private boolean mightContain(String item) {
        for (int seed: hashSeeds) {
            int hash = hash(item, seed);
            if (!bitArray.get(hash % size)) {
                return false;
            }
        }
        return true;
    }

    private int hash(String item, int seed) {
        int hash = 0;
        for (char c: item.toCharArray()) {
            hash = hash*seed+(int)c;
        }
        return hash;
    }

    public static void main(String[] args) {
        int[] seeds = {1,2,3};
        BloomFilterSample bf = new BloomFilterSample(128, seeds);
        bf.add("bloomFilter");
        bf.add("system design");
        System.out.println("Is bloomFilter stored? "+bf.mightContain("bloomFilter"));
        System.out.println("Is bloom Filter stored? "+bf.mightContain("bloom Filter"));
        System.out.println("Is system Design stored? "+bf.mightContain("system Design"));
        System.out.println("Is META stored? "+bf.mightContain("META"));
    }
}
