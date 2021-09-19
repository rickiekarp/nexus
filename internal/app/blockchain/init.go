package blockchain

var chain *BlockChain

func InitBlockChain() {

	chain = &BlockChain{[]*Block{Genesis()}}

	newBlock := NewBlock([]string{"first block"}, chain.blocks[len(chain.blocks)-1])
	chain.AddBlock(*newBlock)

	secondBlock := NewBlock([]string{"second block"}, chain.blocks[len(chain.blocks)-1])
	chain.AddBlock(*secondBlock)

	printChain()
}
