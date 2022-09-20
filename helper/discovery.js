import bonjour from 'bonjour'
import { openSocket } from './socket.js'

export function discoverUsersOnline() {
    bonjour().find({
        type: 'mumble'
    }, function (service) {
        console.log("================================================================")
        console.log("Found service!")
        console.log("ID   -> " + service.txt.id)
        console.log("Name -> " + service.name)
        console.log("Host -> " + service.referer.address)
        console.log("Port -> " + service.port)

        if (service.name == 'urkeev1414') {
            openSocket(service.referer.address, service.port, service.name)
        }
    })
}
discoverUsersOnline()