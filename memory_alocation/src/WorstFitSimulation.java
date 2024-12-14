import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MemoryBlock {
    int start;
    int size;

    public MemoryBlock(int start, int size) {
        this.start = start;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Block(start=" + start + ", size=" + size + ")";
    }
}

public class WorstFitSimulation {

    public static boolean worstFitAllocate(List<MemoryBlock> freeBlocks, int processSize) {
        // Find the block with the largest size
        MemoryBlock worstBlock = null;
        int worstBlockIndex = -1;

        for (int i = 0; i < freeBlocks.size(); i++) {
            MemoryBlock block = freeBlocks.get(i);
            if (block.size >= processSize) {
                if (worstBlock == null || block.size > worstBlock.size) {
                    worstBlock = block;
                    worstBlockIndex = i;
                }
            }
        }

        if (worstBlock != null) {
            System.out.println("Allocating " + processSize + " units from " + worstBlock);
            int newStart = worstBlock.start + processSize;
            int newSize = worstBlock.size - processSize;

            if (newSize > 0) {
                freeBlocks.set(worstBlockIndex, new MemoryBlock(newStart, newSize));
            } else {
                freeBlocks.remove(worstBlockIndex);
            }

            return true;
        } else {
            System.out.println("Allocation failed: No suitable block found");
            return false;
        }
    }

    public static void displayFreeBlocks(List<MemoryBlock> freeBlocks) {
        System.out.println("\nCurrent Free Blocks:");
        if (freeBlocks.isEmpty()) {
            System.out.println("No free blocks available.");
        } else {
            for (MemoryBlock block : freeBlocks) {
                System.out.println(block);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<MemoryBlock> freeBlocks = new ArrayList<>();
        freeBlocks.add(new MemoryBlock(0, 100));
        freeBlocks.add(new MemoryBlock(0, 200));
        freeBlocks.add(new MemoryBlock(0, 300));
        freeBlocks.add(new MemoryBlock(0, 500));

        displayFreeBlocks(freeBlocks);

        while (true) {
            System.out.print("\nEnter process size (or -1 to exit): ");
            int processSize = scanner.nextInt();

            if (processSize == -1) {
                break;
            }

            worstFitAllocate(freeBlocks, processSize);
            displayFreeBlocks(freeBlocks);
        }

        System.out.println("\nSimulation Exits.");
        scanner.close();
    }
}
