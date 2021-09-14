package friends;

import java.util.*;
import java.io.*;

public class Tester {
    public static void main(String[] args) throws FileNotFoundException {
    	

    	int[] intArray = new int[20];
    	int count = 1;
    	for (int i = 0; i<intArray.length; i++) {
    		intArray[i] = i;
    	}
    	
    
    	
    	while (count != 10) {
    		System.out.println(intArray[count]);
    		count = count +1;
    	}
    	
    	/*
        Graph a = new Graph(new Scanner(new File("sptest1.txt")));
        Graph b = new Graph(new Scanner(new File("subtest3.txt")));
        Graph c = new Graph(new Scanner(new File("assnsample.txt")));
        Graph d = new Graph(new Scanner(new File("sptest4.txt")));
        Graph e = new Graph(new Scanner(new File("subtest5.txt")));
        Graph f = new Graph(new Scanner(new File("subtest1_2.txt")));
        Graph g = new Graph(new Scanner(new File("clqtest4.txt")));
        Graph h = new Graph(new Scanner(new File("subtest4.txt")));
        Graph i = new Graph(new Scanner(new File("conntest6.txt")));
        
        System.out.println(Friends.shortestChain(a, "aparna", "kaitlin"));
        System.out.println(Friends.shortestChain(b, "kaitlin", "nick"));
        System.out.println(Friends.shortestChain(c, "nick", "aparna"));
        System.out.println(Friends.shortestChain(d, "p1", "p50"));
        System.out.println(Friends.shortestChain(e, "p1", "p10"));
        System.out.println(Friends.shortestChain(e, "p301", "p198"));
        
        System.out.println(Friends.cliques(f, "cornell"));
        System.out.println(Friends.cliques(f, "rutgers"));
        System.out.println(Friends.cliques(b, "rutgers"));
        System.out.println(Friends.cliques(g, "rutgers"));
        System.out.println(Friends.cliques(c, "rutgers"));
        System.out.println(Friends.cliques(e, "rutgers"));
        
        System.out.println(Friends.connectors(f));
        System.out.println(Friends.connectors(g));
        System.out.println(Friends.connectors(b));
        System.out.println(Friends.connectors(h));
        System.out.println(Friends.connectors(c));
        System.out.println(Friends.connectors(i));
        */
    	return;
    }
    public static void main2(String[] args) throws FileNotFoundException {
    	
    }
}