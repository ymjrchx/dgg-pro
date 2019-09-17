import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import os
from sklearn.linear_model import LogisticRegression
from sklearn.feature_selection import RFE

"""
1. missing_ratio:根据缺失率进行选择
2. low_variance_filter:进行低方差过滤
3. correlation:高相关过滤
4. RandomForest_select:随机森林过滤
5. basckward_select：后向选择
6. forward_select:前向选择
7. 
"""


def missing_ratio(dataframe, rate = 0):
    """
    计算缺失比率,显示缺失比率小于一定值的特征
    Args:
        dataframe:
        rate:阈值，缺失率高于此值的去掉,低于此值的保留

    Returns:
        返回需要保留的特征  缺失值低于阈值的特征list

    """
    missing_series = dataframe.isnull().sum() / len(dataframe) * 100
    columns_lter = dataframe.columns
    variables = []
    if rate == 0:
        return missing_series
    else:
        for i in range(len(columns_lter)):
            if missing_series[i] <= rate:
                variables.append(columns_lter[i])
            else:
                print('%s 缺失率:%s 太高' % (columns_lter[i], missing_series[i]))
        return variables


def low_variance_filter(dataframe, threshold = 10):
    """
    低方差滤波
    先计算所有变量的⽅差⼤⼩，然后删去其中最⼩的⼏个。需要注意的⼀点是：⽅差与数据范围相关的，因此在采⽤该⽅法前需要对数据做归⼀化处理。
    这个方法可以从数据集中识别和删除常量变量，方差小的变量对目标变量影响不大，所以可以放心删去。
    Args:
        self:
        dataframe:
        threshold: 阈值，方差高于此值的保留,低于此值的去除

    Returns:
        返回方差大于阈值的特征list

    """
    var_sample = dataframe.var()
    variable = []
    for i in range(0, len(dataframe)):
        if var_sample[i] >= threshold:
            print(var_sample[i])
            variable.append(dataframe[i])

    return variable


def correlation(dataframe, threshold = 0.9):
    """
    高相关滤波¶
    通常情况下，如果⼀对变量之间的相关性⼤于0.5-0.6，那就应该考虑是否要删除⼀列了。
    具有高相关性的一对变量会增加数据集中的多重共线性，所以用这种方法删去其中一个是有必要的。
    它们具有相似的趋势并且可能携带类似的信息。同理，这类变量的存在会降低某些模型的性能（例如线性和逻辑回归模型）
    Args:
        dataframe:
        threshold:阈值，高于此值的去掉,低于此值的保留

    Returns:
        相关性较高的特征  {'table_id': {'融资订单总金额': 0.9239415664127434}}
    """
    corr_df = dataframe.corr().reset_index()
    columns = corr_df.columns.values.tolist()
    high_corre = {}
    for i in range(len(columns)):
        for j in range(i + 1 + 1,len(columns)): # reset_index后会多出一列出来
            score = corr_df.iloc[i][columns[j]]
            if score > threshold:
                high_corre[columns[i]] = {columns[j]:score}

    return high_corre


def RandomForest_select(dataframe, num):
    """
    随机森林 传进来的数据已经做好了必要的特征工程
    随机森林是⼀种⼴泛使⽤的特征选择算法，它会⾃动计算各个特征的重要性，所以⽆需单独编程。这有助于我们选择较⼩的特征⼦集。
    在开始降维前，我们先把数据转换成数字格式，因为随机森林只接受数字输⼊。同时，ID这个变量虽然是数字，但它⽬前并不重要，所以可以删去。
    Args:
        dataframe:
        num:

    Returns:

    """
    from sklearn.ensemble import RandomForestRegressor
    model = RandomForestRegressor(random_state=1, max_depth=10)
    features = dataframe.columns

    model.fit(dataframe, dataframe.label[:5000])

    importances = model.feature_importances_
    indices = np.argsort(importances[0:num])  # top 10 features
    plt.title('Feature Importances')
    plt.barh(range(len(indices)), importances[indices], color='b', align='center')
    plt.yticks(range(len(indices)), [features[i] for i in indices])
    plt.xlabel('Relative Importance')
    plt.show()

    from sklearn.feature_selection import SelectFromModel
    feature = SelectFromModel(model)
    Fit = feature.fit_transform(df, df.label)
    return indices


def basckward_select(dataframe):
    """
    反向 特 征 消 除 （Backward Feature Elimination ）
    先获取数据集中的全部n个变量，然后⽤它们训练⼀个模型。
    计算模型的性能。
    在删除每个变量（n次）后计算模型的性能，即我们每次都去掉⼀个变量，⽤剩余的n-1个变量训练模型。
    确定对模型性能影响最⼩的变量，把它删除。
    重复此过程，直到不再能删除任何变量。
    在 构 建 线 性 回 归 或Logistic 回 归 模 型 时 ， 可 以 使 ⽤ 这 种 ⽅ 法

    Args:
        dataframe:

    Returns:
        变量重要性排名
    """
    lreg = LogisticRegression()
    rfe = RFE(lreg, 10) # 指定算法和要选择的特征数量
    rfe = rfe.fit_transform(dataframe, dataframe.label) # 返回反向特征消除输出的变量列表

    return rfe.ranking_ # ⽤来检查变量排名


def forward_select(dataframe, threshold = 10):
    """
    前 向 特 征 选 择 （Forward Feature Selection ）
    前向特征选择其实就是反向特征消除的相反过程，即找到能改善模型性能的最佳特征，⽽不是删除弱影响特征。它背后的思路如下所述：
    选择⼀个特征，⽤每个特征训练模型n次，得到n个模型。
    选择模型性能最佳的变量作为初始变量。
    每次添加⼀个变量继续训练，重复上⼀过程，最后保留性能提升最⼤的变量。
    ⼀直添加，⼀直筛选，直到模型性能不再有明显提⾼。
    Args:
        dataframe:
    Returns:
        [注]：前向特征选择和反向特征消除耗时较久，计算成本也都很高，所以只适用于输入变量较少的数据集。

    """
    from sklearn.feature_selection import f_regression

    # 返回⼀个数组，其中包括变量F值和每个F对应的p值。
    ffs = f_regression(dataframe.drop(axis=1, inplace=False, columns='label'), dataframe.label)

    variable = []

    for i in range(0, len(df.columns) - 1):

        if ffs[0][i] >= threshold:  # 在这⾥，我们选择F值⼤于10的变量
            variable.append(df.columns[i])
    return variable


if __name__ == '__main__':
    # 当前文件的路径
    pwd = os.getcwd()
    # 当前文件的父路径
    father_path = os.path.abspath(os.path.dirname(pwd) + os.path.sep + ".")
    # 当前文件的前两级目录
    grader_father = os.path.abspath(os.path.dirname(pwd) + os.path.sep + "..")

    file = pd.read_excel(father_path + '/data/' + '二分类样本数据.xlsx')
    df = file[continuous_feature]
    print(file.head())

    filter_column = missing_ratio(df,rate=10)
    print(filter_column)

    # 低方差滤波，先填充缺失值
    for i in range(len(df.columns)):
        column = df.columns[i]
        if i % 2 == 0:
            df[column].fillna(df[column].median, inplace=True)
        else:
            df[column].fillna(df[column].mode()[0], inplace=True)

    filter_column = low_variance_filter(dataframe=df, threshold=10)
    print(filter_column)