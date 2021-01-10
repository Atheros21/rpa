using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class UiManager : MonoBehaviour
{
    [SerializeField] private Button quitApp;
    [SerializeField] private TextMeshProUGUI country;
    [SerializeField] private TextMeshProUGUI toalCases;
    [SerializeField] private TextMeshProUGUI dailyNew;
    [SerializeField] private TextMeshProUGUI perMillion;
    [SerializeField] private TextMeshProUGUI kills;
    [SerializeField] private string path;
    private string fileData;
    private string[] lines;

    private void Awake()
    {
        fileData = System.IO.File.ReadAllText(path);
        lines = fileData.Split("\n"[0]);
    }

    private void Start()
    {
        quitApp.onClick.AddListener(delegate { Application.Quit(); });
    }


    public void LoadInfo(string targetCountry)
    {
        if (lines == null)
        {
            fileData = System.IO.File.ReadAllText(path);
            lines = fileData.Split("\n"[0]);
        }

        foreach (var item in lines)
        {
            string[] lineData = (item.Trim()).Split(","[0]);
            if (lineData[0] == targetCountry)
            {
                country.text = lineData[0];
                toalCases.text = lineData[1];
                dailyNew.text = lineData[2];
                perMillion.text = lineData[4];
                kills.text = lineData[5];
                return;
            }
        }

        country.text = "";
        toalCases.text = "";
        dailyNew.text = "";
        perMillion.text = "";
        kills.text = "";
    }
}
