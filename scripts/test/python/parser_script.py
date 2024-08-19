import csv
import sys
import datetime

print("RAMP_TEST_ANALYSE")

#
# Говнокод для вычисления RPS.
#

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

    total_time = 0

    for timestamp, code in zip(timestamps, responseCodes):
        total_time += timestamp
        try:
            code = int(code)
        except ValueError:
            code = 0
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

    avg_time = total_time / len(timestamps)
    total_ok_counts = 0
    total_half_ok_counts = 0
    total_half_ok_counts_count = 0

    for second, count in code_2xx_3xx_per_second.items():
        total_ok_counts += count
        if avg_time <= second:
            total_half_ok_counts += count
            total_half_ok_counts_count += 1

    total_bad_counts = 0

    for second, count in code_other_per_second.items():
        total_bad_counts += count

    rps = total_half_ok_counts / total_half_ok_counts_count
    rps = int(rps)

    message = '{} Ramp-test. RPS = {}, Total ok requests = {}, Total bad requests = {}'.format(datetime.datetime.now(), rps, total_ok_counts, total_bad_counts)
    message = message.replace(".", "_")
    message = message.replace(",", "_")
    message = message.replace(":", "_")
    message = message.replace(";", "_")
    message = message.replace(" ", "_")
    message = message.replace("-", "_")
    print(message)

