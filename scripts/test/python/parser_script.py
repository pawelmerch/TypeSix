import csv
import sys
import datetime

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

    for second, count in code_2xx_3xx_per_second.items():
        code_2xx.append(count)
    for second, count in code_other_per_second.items():
        code_other.append(count)

    total_2xx_count = 0
    for count in code_2xx:
        total_2xx_count += count

    total_other_count = 0
    for count in code_other:
        total_other_count += count

    half_2xx_count = 0
    for count in code_2xx[int(len(code_2xx) / 2):]:
        half_2xx_count += count

    rps = int(half_2xx_count / int(len(code_2xx) / 2))

    long_message = 'RPS = {}, Total ok requests = {}, Total bad requests = {}'.format(datetime.datetime.now(), rps, total_2xx_count, total_other_count)

    short_message = 'Ramp_test_{}'
    short_message = long_message.replace(".", "_")
    short_message = long_message.replace(",", "_")
    short_message = long_message.replace(":", "_")
    short_message = long_message.replace(";", "_")
    short_message = long_message.replace(" ", "_")
    short_message = long_message.replace("-", "_")

    os.environ["SHORT_MESSAGE"] = short_message
    os.environ["LONG_MESSAGE"] = long_message


