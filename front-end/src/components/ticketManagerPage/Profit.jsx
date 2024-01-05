import React, { useState, useEffect } from 'react';
import { PieChart, Cell, Pie, ComposedChart, Line, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import useAuth from '../../hooks/useAuth';
import { subMonths, startOfMonth, endOfMonth, format } from 'date-fns';
import { da } from 'date-fns/locale';

const CombinedColumnLineChart = () => {
  const auth = useAuth();

  async function getData(last12months) {
    const dataResponse = { total: 0, arr: [] };
    for (let i of last12months) {
      try {
        // Địa chỉ API và tham số
        const apiUrl = `http://localhost:8080/transactions/monthly-revenue?year=${i.year}&month=${i.month}`;

        // Gọi API bằng phương thức GET
        const response = await fetch(apiUrl, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${auth.accessToken}`
          }
        });
        // Kiểm tra trạng thái của response
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        else {
          // Chuyển đổi response thành JSON
          const result = await response.json();
          // Cập nhật state với dữ liệu từ API
          dataResponse.arr.push(result);
          dataResponse.total += result.total;
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    }
    setData(dataResponse);
  }

  function getLast12Months() {
    const currentDate = new Date();
    const last12Months = [];

    for (let i = 0; i < 12; i++) {
      const startDate = startOfMonth(subMonths(currentDate, i));
      const endDate = endOfMonth(subMonths(currentDate, i));

      last12Months.push({
        year: format(startDate, 'yyyy'),
        month: format(startDate, 'MM'),
        startDate: format(startDate, 'yyyy-MM-dd'),
        endDate: format(endDate, 'yyyy-MM-dd')
      });
    }

    return last12Months;
  }

  const last12months = getLast12Months();
  const [data, setData] = useState({ total: 0, arr: [] });
  let dataTemp = {
    total: 0,
    arr: []
  }


  useEffect(() => {
    getData(last12months);
  }, []);

  useEffect(() => {
    console.log(data);
  }, [data]);


  const colors = [
    '#8884d8', '#82ca9d', '#ffc658', '#ff7f50',
    '#5e5e5e', '#b3b3b3', '#ffcc29', '#ff7043',
    '#a2d5f2', '#ff9999', '#66b3ff', '#99ff99'
  ];

  const calculatePercent = (value, total) => ((value / total) * 100).toFixed(2) + '%';
  return (
    <>
      <div className="chart-container p-4" style={{ backgroundColor: '#f0f0f0' }}>
        <div className='d-flex justify-content-around align-items-center'>
          <div className='fs-4 m-2 p-2 bg-white shadow border border-2 rounded-5'
            style={{ width: '300px', height: '150px' }}>
            Tổng doanh thu
          </div>
          <div className='m-2 p-4 bg-white shadow border border-2 rounded-5'>
            <h4>PHẦN TRĂM DOANH THU THEO THÁNG</h4>
            <PieChart width={400} height={400}>
              <Pie
                dataKey="total"
                data={data.arr}
                cx={200}
                cy={200}
                outerRadius={80}
                label={({ yearMonth, percent }) => `${yearMonth} - ${percent}%`}
              >
                {data.arr.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={colors[index % colors.length]} />
                ))}
              </Pie>
              <Tooltip formatter={(value, name, props) => [`${value} (${calculatePercent(value, props.payload.total)})`, name]} />
              <Legend />
            </PieChart>
          </div>
        </div>
        {/* <ComposedChart width={600} height={300} data={data} margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
          <XAxis dataKey="name" />
          <YAxis />
          <CartesianGrid strokeDasharray="3 3" />
          <Tooltip />
          <Legend />
          <Bar dataKey="pv" barSize={20} fill="#8884d8" />
          <Line type="monotone" dataKey="uv" stroke="#82ca9d" />
        </ComposedChart> */}
      </div>

    </>
  );
};

export default CombinedColumnLineChart;
