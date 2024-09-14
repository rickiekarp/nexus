package queue

type MyQueueElement struct {
	Metric int64 // whatever you want
}

const MAX_QUEUE_SIZE = 512

type Queue struct {
	content   [MAX_QUEUE_SIZE]MyQueueElement
	readHead  int
	writeHead int
	Size      int64
}

func NewQueue() *Queue {
	return &Queue{}
}

func (q *Queue) Push(e MyQueueElement) bool {
	if q.Size >= MAX_QUEUE_SIZE {
		return false
	}
	q.content[q.writeHead] = e
	q.writeHead = (q.writeHead + 1) % MAX_QUEUE_SIZE
	q.Size++
	return true
}

func (q *Queue) Pop() (MyQueueElement, bool) {
	if q.Size <= 0 {
		return MyQueueElement{1}, false
	}
	result := q.content[q.readHead]
	q.content[q.readHead] = MyQueueElement{}
	q.readHead = (q.readHead + 1) % MAX_QUEUE_SIZE
	q.Size--
	return result, true
}

func (q *Queue) IsEmpty() bool {
	return q.Size == 0
}

func (q *Queue) IsFull() bool {
	return q.Size >= MAX_QUEUE_SIZE
}
