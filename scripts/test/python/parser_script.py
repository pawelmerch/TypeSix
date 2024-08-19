import csv
import sys
import datetime

with open(sys.argv[1], newline='') as csvfile:
    spam_reader = csv.reader(csvfile)

    lines = []

    for line in spam_reader:
        lines.append(line)

    header = lines[0]

    timestamps = [int(int(line[0]) / 1000) for line in lines[1:]]
    responseCodes = [line[3] for line in lines[1:]]

    code_2xx_3xx_per_second = dict()
    code_other_per_second = dict()

    for timestamp, code in zip(timestamps, responseCodes):
        code = int(code)
        if 200 <= code and code < 400:
            if timestamp in code_2xx_3xx_per_second:
                code_2xx_3xx_per_second[timestamp] += 1
            else:
                code_2xx_3xx_per_second[timestamp] = 1
        else:
            if timestamp in code_other_per_second:
                code_other_per_second[timestamp] += 1
            else:
                code_other_per_second[timestamp] = 1

    total_ok_counts = 0

    for second, count in code_2xx_3xx_per_second.items():
        total_ok_counts += count

    total_bad_counts = 0

    for second, count in code_other_per_second.items():
        total_ok_counts += count

    rps = total_ok_counts / len(code_2xx_3xx_per_second)
    rps = int(rps)

    message = '{} Ramp-test. RPS = {}, Total ok requests = {}, Total bad requests = {}'.format(datetime.datetime.now(), rps, total_ok_counts, total_bad_counts)
    message = message.replace(".", "_")
    message = message.replace(",", "_")
    message = message.replace(":", "_")
    message = message.replace(";", "_")
    message = message.replace(" ", "_")
    message = message.replace("-", "_")
    print(message)

