rootProject.name = 'JavaFXApps'

include 'AppUpdater'

include 'BotManager';           project(':BotManager').projectDir = new File(rootDir, 'BotManager')
include 'BotLib';               project(':BotLib').projectDir = new File(rootDir, 'BotManager/BotLib')

include 'FilelistCreator'

include 'QAAccountManager'

include 'SHA1Pass'

include 'api'
include 'impl'
include 'core'