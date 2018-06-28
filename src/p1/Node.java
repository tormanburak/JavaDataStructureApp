package p1;

public class Node {
	public int date;		// data item (key)
	public double dData;	// data item
	
	public Node leftChild;		// this node's left child
	public Node rightChild;		// this node's right child
	
	public void displayNode() 	// display ourselves
	{
		System.out.print("{");
		System.out.print(date);
		System.out.print(", ");
		System.out.print(dData);
		System.out.print("} ");
	}
	public double getdData(){
		return dData;
	}
}