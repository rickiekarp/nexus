package blockchain

import (
	"math/rand"
	"net/http"

	"github.com/gorilla/mux"
	"github.com/sirupsen/logrus"
)

func MakeMuxRouter() http.Handler {
	muxRouter := mux.NewRouter()
	muxRouter.HandleFunc("/", handleGetBlockchain).Methods("GET")
	muxRouter.HandleFunc("/addNode", addNewNodeToChain).Methods("GET")
	muxRouter.HandleFunc("/addBlock", addNewBlock).Methods("GET")
	return muxRouter
}

func handleGetBlockchain(w http.ResponseWriter, r *http.Request) {
	PrintChain()
}

func addNewNodeToChain(w http.ResponseWriter, r *http.Request) {
	Network.AddNewNode(rand.Int63n(100))
	PrintChain()
}

func addNewBlock(w http.ResponseWriter, r *http.Request) {
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
