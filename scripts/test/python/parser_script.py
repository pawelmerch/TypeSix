import csv
import sys

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


    for timestamp, code in zip(timestamps, responseCodes):
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

    code_2xx = []
    code_other = []

    for key in sorted(code_2xx_3xx_per_second):
        count = code_2xx_3xx_per_second[key]
        code_2xx.append(count)

    for key in sorted(code_other_per_second):
        count = code_other_per_second[key]
        code_other.append(count)

    total_2xx_count = 0
    for count in code_2xx:
        total_2xx_count += count

    total_other_count = 0
    for count in code_other:
        total_other_count += count

    total_len = len(code_2xx)
    left = int(total_len / 3)
    right = int(total_len / 3 * 2)
    target_slice = code_2xx[left:right]

    target_2xx_count = 0
    for count in target_slice:
        target_2xx_count += count

    rps = int(target_2xx_count / len(target_slice))

    message = 'RPS = {}, Total ok requests = {}, Total bad requests = {}'.format(rps, total_2xx_count, total_other_count)

    print(message)



