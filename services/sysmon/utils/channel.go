package utils

func IsChannelOpen(channel chan bool) bool {
	// return false if the given channel is nil (since it doesn't exist)
	if channel == nil {
		return false
	}

	// check if the channel is open
	select {
	case <-channel:
		return false
	default:
		return true
	}
}
