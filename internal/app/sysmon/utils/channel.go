package utils

func IsChannelOpen(channel chan bool) bool {
	select {
	case <-channel:
		return false
	default:
		return true
	}
}
