// Class to traverse the tree.

class DepthFirstIterator
{
    public static void traverse(Visitable root, Visitor visitor)
    {
        // traverses through the nodes and child nodes recursively
        // till it reaches an OperandNode.
        if (root instanceof OperandNode)
        {
            root.accept(visitor);
            return;
        }
        if (root instanceof BinOpNode)
        {
            BinOpNode opNode = (BinOpNode) root;
            DepthFirstIterator.traverse(opNode.getLeft(), visitor);
            DepthFirstIterator.traverse(opNode.getRight(), visitor);
            opNode.accept(visitor);
            return;
        }
        if (root instanceof UnaryOpNode)
        {
            UnaryOpNode opNode = (UnaryOpNode) root;
            DepthFirstIterator.traverse(opNode.getSubNode(), visitor);
            opNode.accept(visitor);
            return;
        }
        throw new RuntimeException("Instance root has a bad type!");
    }
}