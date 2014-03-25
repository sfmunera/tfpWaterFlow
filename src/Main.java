import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;


public class Main {

    public static void main(String[] args) throws IOException {
        /*Generate Data*/
        // 80000000 is about as big as this implementation can handle;
        int n = 5000;
        double alpha = 0.5;
        DataGenerator dg = new DataGenerator();
        dg.GenerateData(n,alpha);


        /* Sort */
        try{
            IOVersion(n);
        }
        catch (Exception e){e.printStackTrace(); }

        //EdgeFinder search = new EdgeFinder(n);
        //System.out.println(search.getEdgesFrom(0));



        /* Testing what happens with huge data on small machine-- can ignore
        int[] nums = new int[50000000];
        for (int i = 0; i < 50000000; i++){
            nums[i] = (int)Math.random()*50000000;
        }
        Arrays.sort(nums);
        */

		/* Origional code for generating data
		Scanner in = new Scanner(new File("edges.txt"));
		
		int N = in.nextInt();
		Graph G = new Graph(N);
		while (in.hasNextInt()) {
			int u = in.nextInt();
			int v = in.nextInt();
			
			G.addEdge(u, v);
		}

        DataGenerator dg = new DataGenerator();
        dg.GenerateData(40,0.5);
		
		System.out.println(TopologicalSorting.TopologicalSortBFS(G));
		System.out.println(Arrays.toString(LongestPath.LongestPathDP(G)));
		System.out.println(Arrays.toString(LongestPath.LongestPathTimeForward(G, 2)));
		
		in.close();
		*/
		System.exit(0);
	}
    public static void IOVersion(int n) throws Exception{
        File edgesFile = new File("edgeData"+ n + ".dat");
        System.out.println("Beginning sort by Origin");
        IOSort originSorter = new IOSort(edgesFile, n, "originSorted");
        originSorter.sortSegments();
        originSorter.mergeSort();

        printData(n, "originSorted");


        System.out.println("Beginning sort by Dest");
        SortByDestination destSorter = new SortByDestination(n);
        destSorter.sort(edgesFile);
        printData(n, "destSorted");
    }
    public static void printData(int n, String filenamepart) throws IOException{
        RandomAccessFile in = new RandomAccessFile(filenamepart + n + ".dat","r");
        FileChannel fc = in.getChannel();
        int i = 0;
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY,0,fc.size());
        while(mbb.hasRemaining()){

            System.out.println(i++ + ": " + mbb.getInt() + ", " + mbb.getInt());
        }
        System.out.println("-----------");
    }
}
