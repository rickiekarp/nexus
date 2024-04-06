package blockchain

import (
	"math/rand"
	"net/http"

	"github.com/sirupsen/logrus"
)

func HandleGetBlockchain(w http.ResponseWriter, r *http.Request) {
	PrintChain()
}

func AddNewNodeToChain(w http.ResponseWriter, r *http.Request) {
	Network.AddNewNode(rand.Int63n(100))
	PrintChain()
}

func AddNewBlock(w http.ResponseWriter, r *http.Request) {
	_, _, err := GenerateNewBlock(MasterValidator)

	if err != nil {
		logrus.Error(err)
	}

	winnerNode, _ := Network.SelectWinner()
	logrus.Info("Winner:", winnerNode)

	// reward validator
	winnerNode.Stake += 10

	PrintChain()
}
