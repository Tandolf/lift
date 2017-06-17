#!/usr/bin/env python3

import requests
import sys

try:
    url = sys.argv[1]
    r = requests.get(url)
except requests.exceptions.ConnectionError as e:
    print('Could not connect to "%s", reason: "%s"' % (str(sys.argv), str(e)))
    exit(9)

if r.status_code == 200:
    j = r.json()
    try:
        status = j['status']
        if status == 'UP':
            exit(0)
        else:
            print('Health check did not pass. Current application status: "%s"' % str(status))
    except KeyError as e:
        print('Key "%s" not defined in json response: "%s"' % (str(e), str(j)))
        exit(9)
else:
    print('Could not connect to "%s" return status was: "%s"' % (str(sys.argv), str(r.status_code)))
    exit(-9)
