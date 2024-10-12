package queue

import "git.rickiekarp.net/rickie/nexusform"

const MAX_QUEUE_SIZE = 129536
const QUEUE_PROCESSING_BATCH_COUNT = 250

type Queue struct {
	content   [MAX_QUEUE_SIZE]nexusform.HubQueueEventMessage
	readHead  int
	writeHead int
	Size      int
}

func NewQueue() *Queue {
	return &Queue{}
}

func (q *Queue) Push(e nexusform.HubQueueEventMessage) bool {
	if q.Size >= MAX_QUEUE_SIZE {
		return false
	}
	q.content[q.writeHead] = e
	q.writeHead = (q.writeHead + 1) % MAX_QUEUE_SIZE
	q.Size++
	return true
}

func (q *Queue) Pop() (*nexusform.HubQueueEventMessage, bool) {
	if q.Size <= 0 {
		return nil, false
	}
	result := q.content[q.readHead]
	q.content[q.readHead] = nexusform.HubQueueEventMessage{}
	q.readHead = (q.readHead + 1) % MAX_QUEUE_SIZE
	q.Size--
	return &result, true
}

func (q *Queue) IsEmpty() bool {
	return q.Size == 0
}

func (q *Queue) IsFull() bool {
	return q.Size >= MAX_QUEUE_SIZE
}
