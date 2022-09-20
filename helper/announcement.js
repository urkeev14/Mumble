import bonjour from 'bonjour'
import { v4, stringify as stringy } from 'uuid';

import { generateUsername } from "unique-username-generator"


const service = bonjour()

export function publishUserOnline() {
    let i = 0
    while (i < 10) {
        service.publish({
            name: generateUsername(),
            type: 'mumble',
            protocol: 'tcp',
            port: 2006 + i,
            txt: {
                id: String(v4())
            }
        })
        i++
    }
}

export function unpublishAll() {
    service.unpublishAll()
}

publishUserOnline()