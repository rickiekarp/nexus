package blockchain

import (
	"crypto/sha256"
	"fmt"
	"time"
)

type BlockChain struct {
	blocks []*Block
}

type Block struct {
	id           int64
	timestamp    time.Time
	transactions []string
	prevHash     []byte
	Hash         []byte
}

func Genesis() *Block {
	currentTime := time.Now()
	genesisBlock := &Block{
		id:           1,
		timestamp:    currentTime,
		transactions: []string{"genesis block"},
		prevHash:     []byte{},
		Hash:         nil,
	}
	genesisBlock.Hash = NewHash(currentTime, genesisBlock.transactions, []byte{})
	return genesisBlock
}

func NewBlock(transactions []string, prevBlock *Block) *Block {
	currentTime := time.Now()
	return &Block{
		id:           prevBlock.id + 1,
		timestamp:    currentTime,
		transactions: transactions,
		prevHash:     prevBlock.Hash,
		Hash:         NewHash(currentTime, transactions, prevBlock.Hash),
	}
}

func (chain *BlockChain) AddBlock(block Block) {
	chain.blocks = append(chain.blocks, &block)
}

func NewHash(time time.Time, transactions []string, prevHash []byte) []byte {
	input := append(prevHash, time.String()...)
	for transaction := range transactions {
		input = append(input, string(rune(transaction))...)
	}
	hash := sha256.Sum256(input)
	return hash[:]
}

func printChain() {
	for _, block := range chain.blocks {
		printBlockInformation(block)
		fmt.Println("")
	}
}

func printBlockInformation(block *Block) {
	fmt.Printf("BlockID: %x\n", block.id)
	fmt.Printf("Previous hash: %x\n", block.prevHash)
	fmt.Printf("timestamp: %s\n", block.timestamp)
	fmt.Printf("hash: %x\n", block.Hash)
	printTransactions(block)
}

func printTransactions(block *Block) {
	for _, transaction := range block.transactions {
		fmt.Printf("data: %s\n", transaction)
	}
}
