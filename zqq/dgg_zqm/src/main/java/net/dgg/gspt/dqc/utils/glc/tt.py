import sys


def guanlianci(x):
    import synonyms as sy
    y = sy.nearby(x)
    y = y[0]
    print(y)


if __name__ == "__main__":
    guanlianci(sys.argv[1])
